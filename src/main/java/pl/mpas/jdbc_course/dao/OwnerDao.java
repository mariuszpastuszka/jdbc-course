package pl.mpas.jdbc_course.dao;

import pl.mpas.jdbc_course.model.Owner;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface OwnerDao {

    default List<Owner> findAll() {
        return Collections.emptyList();
    }

    default List<Owner> findByPredicate(Predicate<Owner> filter) {
        return findAll().stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    default Owner saveOwner(Owner toSave) {
        return toSave;
    }

    default boolean deleteOwner(Owner toDelete) {
        return false;
    }
}
