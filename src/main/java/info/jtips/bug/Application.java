package info.jtips.bug;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.Filter;
import org.hibernate.Session;

import java.util.List;

public class Application {

    private static final boolean ENABLE_FILTER = true;
    private static EntityManagerFactory entityManagerFactory;

    public static void main(String[] args) {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");

        System.out.println("=== INIT ===");
        initialize();
        System.out.println("=== SELECT#1 ===");
        findBooks(ENABLE_FILTER)
                .forEach(System.out::println);
        System.out.println("=== SELECT#2 ===");
        findBooks(ENABLE_FILTER)
                .forEach(System.out::println);
    }

    private static void initialize() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(new Book("Java Persistence with Hibernate"));
            entityManager.persist(new Book("Beginning Hibernate 6: Java Persistence from Beginner to Pro"));
            entityManager.persist(new Book("Hibernate : A Developer's Notebook (en anglais)"));
            entityManager.persist(new Book("Hibernate: A J2EE™ Developer's Guide", true));
            entityManager.getTransaction().commit();
        }
    }

    private static List<Book> findBooks(boolean excludeDisabled) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            if (excludeDisabled) {
                Session session = entityManager.unwrap(Session.class);
                Filter filter = session.enableFilter(Book.IS_NOT_DISABLED);
                filter.setParameter("disabled", false);
            }
            List<Book> result = entityManager.createQuery("select b from Book b", Book.class)
                    .setHint("org.hibernate.cacheable", "true")
                    .getResultList();
            entityManager.getTransaction().commit();
            return result;
        }
    }
}
