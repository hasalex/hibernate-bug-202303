package info.jtips.bug;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.util.Objects;
import java.util.UUID;


@FilterDef(name = Book.IS_NOT_DISABLED, parameters = {@ParamDef(name = "disabled", type = Boolean.class)})
@Filter(name = "is-not-disabled", condition = "disabled = :disabled")
@Entity
public class Book {

    public static final String IS_NOT_DISABLED = "is-not-disabled";

    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    public Book(String title, boolean disabled) {
        this.title = title;
        this.disabled = disabled;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private boolean disabled;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Book %s (%s)", title, id);
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
