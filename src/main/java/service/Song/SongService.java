package service.Song;

import model.Song;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class SongService implements ISongService {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Iterable<Song> findAll() {
        EntityManager entityManager = sessionFactory.createEntityManager();
        TypedQuery<Song> query = entityManager.createQuery("SELECT s FROM Song AS s", Song.class);
        return query.getResultList();
    }

    @Override
    public Song findById(int id) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        String querytr = "SELECT c FROM Song AS c WHERE c.id = :id";
        TypedQuery<Song> query = entityManager.createQuery(querytr,Song.class);
        query.setParameter("id",id);
        return query.getSingleResult();
    }

    @Override
    public Song save(Song song) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            if (song.getId()>0){
                session.merge(song);
            }else {
                session.persist(song);
            }
            transaction.commit();
            return song;
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
            if (transaction != null){
                transaction.rollback();
            }
        }
        return null;
    }

    @Override
    public Song delete(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            Song song = findById(id);
            transaction = session.beginTransaction();
            session.delete(song);
            transaction.commit();
            return song;
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
            if (transaction != null){
                transaction.rollback();
            }
        }
        return null;
    }

}
