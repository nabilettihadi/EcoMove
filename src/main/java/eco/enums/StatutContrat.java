package main.java.eco.enums;

public enum StatutContrat {
    EN_COURS,
    TERMINE,
    SUSPENDU;


    public static StatutContrat fromInt(int i) {
        switch (i) {
            case 1:
                return EN_COURS;
            case 2:
                return TERMINE;
            case 3:
                return SUSPENDU;
            default:
                throw new IllegalArgumentException("Valeur invalide pour StatutContrat: " + i);
        }
    }
}
