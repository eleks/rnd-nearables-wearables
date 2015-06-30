package com.eleks.utils;

import com.eleks.model.UserCredentials;

public class Constants {

	public static final String TEAMPRO_URL = "https://teampro.eleks.com/en/asd.dll/";
	public static final String TEAMPRO_MONTHLY_DATA_URL = TEAMPRO_URL + "i?noweb=1&n=get-personal-time-monthly-data";
	public static final String TEAMPRO_EMPLOYEES_URL = TEAMPRO_URL + "i?noweb=1&n=w32-user-data-get";
	public static final String TEAMPRO_EMPLOYEES_IMAGE_URL = TEAMPRO_URL + "i?n=w32-document&Kind=HR&Doc=1&ExtID=1&ID=";
	public static final String TEAMPRO_AUTHENTICATION_URL = TEAMPRO_URL + "i?noweb=1&n=personal-worktime-type-lookup";
	public static final String TEAMPRO_ADD_REPORT = TEAMPRO_URL + "i?noweb=1&n=w32-WT-add-new-recrod&";
	public static final String DOMAIN = "eleks-software";
	
	public static final String TRUE = "Y";
	public static final String FALSE = "N";
	
	public static final String SESSION_ID_HEADER_PARRAM = "sessionId";
	
	public static final UserCredentials DEFAULT_CREDENTIALS = new UserCredentials("bogdan.melnychuk", "<PASSWORD>");
	
	public static final class TeamProControllerConstants {
		public static final String MONTHLY_DATA_URL = "/monthly-data";
		
		public static final String USERNAME_REQUEST_PARAM = "username";
		public static final String PASSWORD_REQUEST_PARAM = "password";
	}
	
	public static final class EmployeesControllerConstants {
		public static final String EMPLOYEES_URL = "/employees";
		public static final String EMPLOYEE_IMAGE_URL = "/employee/{id}/photo";
	}
	
	public static final class ReportsControllerConstants {
		public static final String REPORTS_URL = "/monthly-reports";
		public static final String ADD_REPORT_URL = "/add-report";
	}
	
	public static final class AuthenticationControllerConstants {
		public static final String AUTHENTICATION_URL = "/authenticate";
		
		public static final String USERNAME_REQUEST_PARAM = "username";
		public static final String PASSWORD_REQUEST_PARAM = "password";
	}
	
	public static final class InvitationControllerConstants {
		public static final String GET_INVITE_URL = "/get-invite";
		public static final String ACTIVATE_INVITE_URL = "/activate-invite";
		
		public static final String USER_NAME_PARAM = "username";
		public static final String DEVICE_ID_PARAM = "device_id";
		public static final String KEY_PARAM = "key";
		
		public static final String EMAIL_SUFIX = "@eleks.com";
		
		public static final String ACCESS_TOKEN = "accessToken";
	}
	
	public static final class LocationsController {
		public static final String ADD_LOCATION_URL = "add-location";
		public static final String GET_LOCATIONS_URL = "/get-locations/{email}";
	}
	
	public static final class Xml {
		public static final String ROOT = "root";
		public static final String METHOD_RESPONSE = "method_response";
		public static final String NAME = "name";
		public static final String EMPLOYEES_LIST = "wxUsersList";
		public static final String EMPLOYEES_EXTRA_DATA = "wxData";
		public static final String REPORTS_LIST = "PersonalTimeData";
		public static final String XML = "xml";
		public static final String DATA = "data";
		public static final String ROW = "row";
	}
	
	public static final class EmployeeConstants {
		public static final String ID = "ID";
		public static final String LOGIN = "UserLogin";
		public static final String FULL_NAME = "UserFullName";
		public static final String EMAIL = "UserEMail";
		public static final String SKYPE = "SkypeStartURL";
		public static final String DEPARTMENT = "DepartmentName";
		public static final String LEVEL_1 = "UserLevel1";
		public static final String LEVEL_2 = "UserLevel2";
		public static final String KIND_1 = "UserKind1";
		public static final String KIND_2 = "UserKind2";
		public static final String INV_POSITION = "UserInvPosition";
		public static final String INV_PROJECT_PATH = "UserInvProjectPath";
		public static final String INV_SUPERIOR = "UserInvSuperior";
		public static final String DIVISION = "Division";
		public static final String JOB_TITLE = "JobTitleName";
		public static final String JOB_TITLE_AREA = "JobTitleAreaName";
		public static final String OFFICE = "UserOffice";
		
		public static final String USER_REF = "URef";
		public static final String ATTR_ID = "AttrID";
		public static final String DATA_STR = "DataStr";
		public static final String DATA_INT = "DataInt";
		public static final String DATA_DICT = "DataDict";
		
		public static final int ROOM_ATTR_ID = 1;
		public static final int PHONE_ATTR_ID = 28;
		public static final int CAR_MODEL_ATTR_ID = 81;
		public static final int CAR_NUMBER_ATTR_ID = 82;
		public static final int BIRTHDAY_DAY = 21; 
		public static final int BIRTHDAY_MONTH = 57; 
		public static final int SEX = 91; 
	}
	
	public static final class ReportConstants {
		public static final String WORK_TIME_DATE = "WorkTimeDate";
		public static final String WORK_TIME_NAME = "WorkTimeName";
		public static final String WORK_TIME_USER_REF = "WorkTimeUserRef";
		public static final String PROJECT_PATH = "ProjectPath";
		public static final String TYPE_NAME = "TypeName";
		public static final String WORK_TIME_STATUS_NAME = "WorkTimeStatusName";
		public static final String VALID = "Valid";
		public static final String WORK_TIME_TIME = "WorkTimeTime";
	}
}
