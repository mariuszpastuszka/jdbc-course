package pl.mpas.jdbc_course.dao;

import pl.mpas.jdbc_course.model.Dog;

import java.util.Optional;

public interface DogDao {

    default Dog save(Dog toSave) {
        return toSave;
    }

    default boolean deleteDog(Dog toDelete) {
        return false;
    }

    default Optional<Dog> findByOwnerId(Long ownerId) {
        return Optional.empty();
    }
}
