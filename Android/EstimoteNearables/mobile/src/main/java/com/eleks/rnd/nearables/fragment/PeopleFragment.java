package com.eleks.rnd.nearables.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.eleks.rnd.nearables.PreferencesManager;
import com.eleks.rnd.nearables.R;
import com.eleks.rnd.nearables.activity.LoginActivity;
import com.eleks.rnd.nearables.adapter.PersonAdapter;
import com.eleks.rnd.nearables.loader.LoaderIDs;
import com.eleks.rnd.nearables.loader.PersonLoader;
import com.eleks.rnd.nearables.loader.result.PersonLoaderResult;
import com.eleks.rnd.nearables.model.Person;
import com.eleks.rnd.nearables.utils.PersonUtils;
import com.github.johnkil.print.PrintDrawable;
import com.tonicartos.superslim.LayoutManager;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Set;

/**
 * Created by bogdan.melnychuk on 17.07.2015.
 */
public class PeopleFragment extends Fragment implements LoaderManager.LoaderCallbacks<PersonLoaderResult> {
    private static final int SEARCH_MESSAGE = 0;

    private RecyclerView mRecyclerView;
    private PersonAdapter mAdapter;
    private int mHeaderDisplay;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FloatingActionButton fab;
    private String searchConstraint;
    private ActionMode actionMode;
    private OfflineSearchHandler offlineSearchHandler = new OfflineSearchHandler(this);

    private MenuItem searchItem;
    private MenuItem logout;
    private MenuItem addToFav;
    private MenuItem removeFromFav;

    private static final class OfflineSearchHandler extends Handler {
        private final WeakReference<PeopleFragment> reference;

        public OfflineSearchHandler(PeopleFragment employeeFragment) {
            reference = new WeakReference<>(employeeFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            PeopleFragment fragment = reference.get();
            if (fragment.isAdded() && fragment != null) {
                fragment.getLoaderManager().restartLoader(LoaderIDs.PERSON_LOADER.ordinal(), null, reference.get());
            }
        }

    }

    ActionMode.Callback amCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            PeopleFragment.this.actionMode = actionMode;
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.menu_am_persons, menu);
            addToFav = menu.findItem(R.id.ai_favorite);
            removeFromFav = menu.findItem(R.id.ai_unfavorite);

            addToFav.setIcon(
                    new PrintDrawable.Builder(getActivity())
                            .iconTextRes(R.string.ic_star)
                            .iconColorRes(android.R.color.white)
                            .iconSizeRes(R.dimen.fab_icon_size)
                            .build()
            );

            removeFromFav.setIcon(
                    new PrintDrawable.Builder(getActivity())
                            .iconTextRes(R.string.ic_star_outline)
                            .iconColorRes(android.R.color.white)
                            .iconSizeRes(R.dimen.fab_icon_size)
                            .build()
            );

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            Set<Long> favorites = PreferencesManager.getFavorites(getActivity());
            Collection<Person> selected = mAdapter.getSelected();
            switch (menuItem.getItemId()) {
                case R.id.ai_favorite:
                    for(Person p : selected) {
                        favorites.add(p.getEmpId());
                    }
                    break;
                case R.id.ai_unfavorite:
                    for(Person p : selected) {
                        favorites.remove(p.getEmpId());
                    }
                    break;
            }
            PreferencesManager.putFavoritesIds(favorites, getActivity());
            restartLoader();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            mAdapter.clearSelection();
            PeopleFragment.this.actionMode = null;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void restartLoader() {
        getLoaderManager().restartLoader(LoaderIDs.PERSON_LOADER.ordinal(), null, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        restartLoader();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mates, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHeaderDisplay = getResources().getInteger(R.integer.default_header_display);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchItem.expandActionView();
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LayoutManager(getActivity()));
        mAdapter = new PersonAdapter(getActivity(), mHeaderDisplay);
        mAdapter.setPersonCheckeListener(new PersonAdapter.PersonCheckListener() {
            @Override
            public void onPersonCheck(Person p, boolean checked, final Collection<Person> allChecked) {
                if(allChecked.size() > 0) {
                    if(actionMode == null) {
                        getActivity().startActionMode(amCallback);
                    }
                    actionMode.setTitle(allChecked.size() + " selected");
                    showFavAction(allChecked);
                } else {
                    if(PeopleFragment.this.actionMode != null) {
                        actionMode.finish();
                        actionMode = null;
                    }
                }
            }
        });
        mAdapter.setHeaderDisplay(mHeaderDisplay);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Bundle b = new Bundle();
                b.putBoolean(PersonLoader.USE_SERVER_DATA, true);
                getLoaderManager().restartLoader(LoaderIDs.PERSON_LOADER.ordinal(), b, PeopleFragment.this);
            }
        });
    }

    private void showFavAction(Collection<Person> personList) {
        boolean containsFavorite = PersonUtils.containsFavorite(personList);
        boolean containsNonFavorite = PersonUtils.containsNonFavorite(personList);

        if(containsFavorite && containsNonFavorite) {
            addToFav.setVisible(false);
            removeFromFav.setVisible(false);
        } else if(containsFavorite) {
            addToFav.setVisible(false);
            removeFromFav.setVisible(true);
        } else {
            addToFav.setVisible(true);
            removeFromFav.setVisible(false);
        }
    }

    private void toogleToolbar(boolean search) {
        if (search) {
            fab.setVisibility(View.GONE);
        } else {
            fab.setVisibility(View.VISIBLE);
        }
        if(logout != null) {
            logout.setVisible(!search);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(search ? R.color.accent : R.color.primary_dark));
            getActivity().findViewById(R.id.toolbar).setBackgroundResource(search ? R.color.accent : R.color.primary);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        searchItem = menu.findItem(R.id.mi_search);
        logout = menu.findItem(R.id.mi_logout);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                toogleToolbar(true);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                toogleToolbar(false);
                return true;
            }
        });

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Enter keyword...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchConstraint = newText;
                if (offlineSearchHandler != null) {
                    offlineSearchHandler.removeMessages(SEARCH_MESSAGE);
                    offlineSearchHandler.sendMessageDelayed(offlineSearchHandler.obtainMessage(SEARCH_MESSAGE), 600);
                }
                return true;
            }
        });
    }

    @Override
    public Loader<PersonLoaderResult> onCreateLoader(int i, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (!TextUtils.isEmpty(searchConstraint)) {
            bundle.putString(PersonLoader.CONSTRAINT, searchConstraint);
        }
        return new PersonLoader(getActivity(), bundle);
    }

    private void finishActionMode() {
        if(actionMode != null) {
            actionMode.finish();
            actionMode = null;
        }
    }

    @Override
    public void onLoadFinished(Loader<PersonLoaderResult> loader, PersonLoaderResult personLoaderResult) {
        if (personLoaderResult.wasSuccessful()) {
            mAdapter.clearSelection();
            finishActionMode();
            mAdapter.setData(personLoaderResult.getPersons());
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        } else {
            Toast.makeText(getActivity(), personLoaderResult.getException().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<PersonLoaderResult> loader) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_logout:
                PreferencesManager.clear(getActivity());
                Intent myIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(myIntent);
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
