package GameObjects;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Factory {

    public GameObject getRandomObject() {
        int nFruits = Objects.requireNonNull(new File(System.getProperty("user.dir")
                + "/src/GameObjects").list()).length;
        int random = (int) (Math.random() * (nFruits - 8));
//        System.out.println("Random is "+ random);
//        System.out.println("Files size"+ nFruits);
        switch (random) {
            case 0:
                return new Apple();
            case 1:
                return new Lemon();
            case 2:
                int randomExotic = ThreadLocalRandom.current().nextInt(0, 3);
                switch (randomExotic) {
                    case 0:
                        return new TimeFreezer();
                    case 1:
                        return new DoubleScorer();
                    case 2:
                        return new Heart();
                    default:
                        return new Apple();
                }
            case 3:
                return new Guava();
            case 4:
                return new Pear();
            default:
                return new Orange();
        }
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    public GameObject getBomb() {

        int random = (int) (Math.random() * (2));

        switch (random) {
            case 0:
                return new Fatal();
            default:
                return new Dangerous();
        }
    }
}
