package game.objects;

/**
 * Predefenidos para las variables collCode y collides de los objetos.
 * Ambas variables son bitArrays, una indica el tipo y el otro con los tipos que
 * colisionan
 * 
 * @author Project Kevin
 */
public enum CollisionCodes {
    TEAM1    (0b00000000001), TEAM1_COL    (0b11111111100),
    FIRE1    (0b00000000010), FIRE1_COL    (0b11111111100),
    TEAM2    (0b00000000100), TEAM2_COL    (0b11111110011),
    FIRE2    (0b00000001000), FIRE2_COL    (0b11111110011),
    TEAM3    (0b00000010000), TEAM3_COL    (0b11111001111),
    FIRE3    (0b00000100000), FIRE3_COL    (0b11111001111),
    TEAM4    (0b00001000000), TEAM4_COL    (0b11100111111),
    FIRE4    (0b00010000000), FIRE4_COL    (0b11100111111),
    ASTEROID (0b00100000000), ASTEROID_COL (0b01011111111),
    GRAVPOOL (0b01000000000), GRAVPOOL_COL (0b10111111111),
    POWERUP  (0b10000000000), POWERUP_COL  (0b01001010101),
    
    TEAMS    (0b00001010101),
    FIRES    (0b00010101010);

    private final int value;

    CollisionCodes(int value) {
        this.value = value;
    }

    public int getValue() { return value; }
}
