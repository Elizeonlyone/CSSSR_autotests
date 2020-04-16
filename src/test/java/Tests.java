import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Tests {
    SuperHero superHero;

    @Test
    @DisplayName("Создание нового супергероя, выведение на экран")
    public void createSuperHero(){
        superHero = new SuperHero();
        superHero.createNewHero();
        System.out.println(superHero);
    }

    @Test
    @DisplayName("Поиск существующего супергероя, выведение на экран")
    public void findAnExistingSuperhero(){
        superHero = new SuperHero(4831);
        System.out.println(superHero);
    }

    @Test
    @DisplayName("Создание нового супергероя, поиск его по базе, проверка, правильного ли нашли")
    public void checkOutEqualSuperheroes(){
        superHero = new SuperHero();
        superHero.createNewHero();
        SuperHero foundSuperhero = new SuperHero(superHero.getId());
        System.out.println("Найденный объект соответствует созданному ранее?\n" + superHero.equals(foundSuperhero));
    }

    @AfterEach
    public void deleteData(){
        superHero = null;
    }
}
