package com.eleks.service;

import com.eleks.model.db.Nearable;

public interface NearableService extends BaseService<Nearable> {
	Nearable getByUid(String uid);
}
