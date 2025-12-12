package seneca.pmugisha3.bookmanager.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.Locale;

import seneca.pmugisha3.bookmanager.R;
import seneca.pmugisha3.bookmanager.models.Book;

/**
 * A utility class for handling common intents.
 */
public final class IntentUtils {

  /**
   * Private constructor to prevent instantiation.
   */
  private IntentUtils() {}

  /**
   * Creates and launches a web search intent for the book's title.
   *
   * @param context The context from which the intent is launched.
   * @param book    The book to search for.
   */
  public static void searchBook(Context context, Book book) {
    if (book == null || book.getTitle() == null) {
      return;
    }
    String query = Uri.encode(book.getTitle());
    String url = "https://www.google.com/search?q=" + query;
    Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    context.startActivity(browser);
  }

  /**
   * Creates and launches a share intent to share the book's details.
   *
   * @param context The context from which the intent is launched.
   * @param book    The book to share.
   */
  public static void shareBook(Context context, Book book) {
    if (book == null) {
      return;
    }
    String shareText = String.format(Locale.getDefault(), "%s: %s, %s %s (%d)", context.getString(R.string.check_out_this_book), book.getTitle(), context.getString(R.string.by), book.getAuthor(), book.getYear());
    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent.setType("text/plain");
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.book_recommendation));
    shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
    Intent chooser = Intent.createChooser(shareIntent, context.getString(R.string.share_book_via));
    context.startActivity(chooser);
  }
}
