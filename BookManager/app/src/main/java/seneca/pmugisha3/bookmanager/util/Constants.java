package seneca.pmugisha3.bookmanager.util;

/**
 * A utility class to hold constants for the application.
 */
public final class Constants {

  /**
   * Private constructor to prevent instantiation.
   */
  private Constants() {}

  // Keys for saving instance state
  public static final String KEY_TITLE = "title";
  public static final String KEY_AUTHOR = "author";
  public static final String KEY_YEAR = "year";

  // Keys for passing data between activities
  public static final String EXTRA_BOOK = "currentBook";
  public static final String EXTRA_PHOTO = "photo";

  // Tag for logging
  public static final String LOG_TAG_LIFECYCLE = "lifecycle";
}
