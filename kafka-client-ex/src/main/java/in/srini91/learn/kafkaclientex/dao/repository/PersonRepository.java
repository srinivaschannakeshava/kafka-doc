package in.srini91.learn.kafkaclientex.dao.repository;

import in.srini91.learn.kafkaclientex.dao.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
}
