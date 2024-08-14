package jsb.ep4api.constrants;

import java.time.LocalDateTime;

import static java.util.stream.IntStream.rangeClosed;

public class Constants {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    public static final boolean DEFAULT_DELETE_FLAG = false;
    public static final boolean DEFAULT_VERIFY_FLAG = false;

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_ORDER = "asc";

    public static final int[] SORT_ORDERS = rangeClosed(1, 20).toArray();

    public static final String[] USER_PACKAGES = {"FREE", "BASIC", "PREMIUM", "VIP"};

    public static final boolean IS_PACKAGE = true;

    public static final String DEFAULT_EXPIRED_TIME = "30 Days";
    public static final LocalDateTime CURRENT_TIME = LocalDateTime.now();

    public static final String DEFAULT_AVATAR = "blank_avatar.png";
    public static final String DEFAULT_POSTER = "blank_poster.png";

    public static final String REGISTER_SUCCESS_MESSAGE = "User registered successfully!";
    public static final String REGISTER_FAIL_MESSAGE = "Register failed! Error: ";
    public static final String LOGIN_SUCCESS_MESSAGE = "User logged in successfully!";
    public static final String LOGIN_FAIL_MESSAGE = "Username or password is incorrect!";
    public static final String LOGOUT_SUCCESS_MESSAGE = "User logged out successfully!";
    public static final String USER_NOT_FOUND_MESSAGE = "User not found!";
    public static final String USER_UPDATED_MESSAGE = "User updated successfully!";
    public static final String USER_UPDATE_FAIL_MESSAGE = "Update failed! Error: ";
    public static final String USER_DELETED_MESSAGE = "User deleted successfully!";
    public static final String USER_DELETE_FAIL_MESSAGE = "Delete failed! Error: ";


    private Constants() {
        super();
    }
}
