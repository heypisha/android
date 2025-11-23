package seneca.pmugisha3.bookmanager.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Book implements Parcelable {

  private final String title;
  private final String author;
  private final int year;
  public Book(String title, String author, int year) {
    this.title = title;
    this.author = author;
    this.year = year;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public int getYear() {
    return year;
  }

  protected Book(Parcel in) {
    title = in.readString();
    author = in.readString();
    year = in.readInt();
  }

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

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(@NonNull Parcel dest, int flags) {
    dest.writeString(title);
    dest.writeString(author);
    dest.writeInt(year);
  }
}
