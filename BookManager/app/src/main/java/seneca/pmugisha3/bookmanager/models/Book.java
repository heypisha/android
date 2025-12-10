package seneca.pmugisha3.bookmanager.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Represents a book with a title, author, and year of publication.
 * This class implements the Parcelable interface to allow book objects to be passed between activities.
 */
public class Book implements Parcelable {

  // The title of the book.
  private final String title;
  // The author of the book.
  private final String author;
  // The year the book was published.
  private final int year;

  /**
   * Constructs a new Book object.
   *
   * @param title  The title of the book.
   * @param author The author of the book.
   * @param year   The year the book was published.
   */
  public Book(String title, String author, int year) {
    this.title = title;
    this.author = author;
    this.year = year;
  }

  /**
   * Returns the title of the book.
   *
   * @return The book's title.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Returns the author of the book.
   *
   * @return The book's author.
   */
  public String getAuthor() {
    return author;
  }

  /**
   * Returns the year the book was published.
   *
   * @return The book's publication year.
   */
  public int getYear() {
    return year;
  }

  /**
   * Constructs a Book object from a Parcel.
   *
   * @param in The Parcel to read the book's data from.
   */
  protected Book(Parcel in) {
    title = in.readString();
    author = in.readString();
    year = in.readInt();
  }

  /**
   * A CREATOR that generates instances of the Book class from a Parcel.
   */
  public static final Creator<Book> CREATOR = new Creator<>() {
    @Override
    public Book createFromParcel(Parcel in) {
      return new Book(in);
    }

    @Override
    public Book[] newArray(int size) {
      return new Book[size];
    }
  };

  /**
   * Describes the kinds of special objects contained in this Parcelable instance's marshaled representation.
   *
   * @return A bitmask indicating the set of special object types marshaled by this Parcelable object instance.
   */
  @Override
  public int describeContents() {
    return 0;
  }

  /**
   * Flattens this object into a Parcel.
   *
   * @param dest  The Parcel in which the object should be written.
   * @param flags Additional flags about how the object should be written.
   */
  @Override
  public void writeToParcel(@NonNull Parcel dest, int flags) {
    dest.writeString(title);
    dest.writeString(author);
    dest.writeInt(year);
  }
}
