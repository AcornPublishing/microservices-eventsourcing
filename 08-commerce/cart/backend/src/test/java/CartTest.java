import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CartTest {
    //
    @Test
    public void testInvalidRatio() {
        //
        Assertions.assertThrows(InvalidVolumeRatioException.class, () -> {
            new Volume(100, 200, 100);
        });
    }

    @Test
    public void testValidRatio() {
        //
        Volume volume = new Volume(100, 150, 100);
        Assertions.assertTrue(volume.width * 1.5 >= volume.depth);
    }

    @Getter
    public class Volume {
        //
        private int width;
        private int depth;
        private int height;

        public Volume(int width, int depth, int height) {
            //
            if (width * 1.5 < depth) {
                throw new InvalidVolumeRatioException("depth >= width * 1.5");
            }
            this.width = width;
            this.depth = depth;
            this.height = height;
        }
    }

    public class InvalidVolumeRatioException extends RuntimeException {
        //
        public InvalidVolumeRatioException(String message) {
            //
            super(message);
        }
    }
}
