package project.service;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by angel on 03/06/2017.
 */
public class Artwork {

    private final IntegerProperty id;
    private final StringProperty title;
    private final IntegerProperty rating;
    private final StringProperty comment;
    private final StringProperty genre;
    private final StringProperty category;
    private final StringProperty origin;
    private final StringProperty support;
    private final StringProperty creator;
    private final IntegerProperty year;

    public Artwork() {
        this.id = new SimpleIntegerProperty();
        this.title = new SimpleStringProperty();
        this.rating = new SimpleIntegerProperty();
        this.comment = new SimpleStringProperty();
        this.origin = new SimpleStringProperty();
        this.genre = new SimpleStringProperty();
        this.category = new SimpleStringProperty();
        this.support = new SimpleStringProperty();
        this.creator = new SimpleStringProperty();
        this.year = new SimpleIntegerProperty();
    }

    //Getters
    public int getId() {
        return id.get();
    }

    public String getComment() {
        return comment.get();
    }

    public String getTitle() {
        return title.get();
    }

    public int getRating() {
        return rating.get();
    }

    public String getGenre() {
        return genre.get();
    }

    public String getCategory() {
        return category.get();
    }

    public String getSupport() {
        return support.get();
    }

    public String getCreator() {
        return creator.get();
    }

    public int getYear() {
        return year.get();
    }

    //Setters
    public void setId(int id) {
        this.id.set(id);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setRating(int rating) {
        this.rating.set(rating);
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public void setSupport(String support) {
        this.support.set(support);
    }

    public void setCreator(String creator) {
        this.creator.set(creator);
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    //Property Values
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public IntegerProperty ratingProperty() {
        return rating;
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public StringProperty genreProperty() {
        return genre;
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public StringProperty supportProperty() {
        return support;
    }

    public StringProperty creatorProperty() {
        return creator;
    }

    public IntegerProperty yearProperty() {
        return year;
    }



    public String getOrigin() {
        return origin.get();
    }

    public StringProperty originProperty() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin.set(origin);
    }


    @Override
    public String toString() {
        return "Artwork{" +
                "id=" + id +
                ", title=" + title +
                ", rating=" + rating +
                ", comment=" + comment +
                ", genre=" + genre +
                ", category=" + category +
                ", origin=" + origin +
                ", support=" + support +
                ", creator=" + creator +
                ", year=" + year +
                '}';
    }
}
