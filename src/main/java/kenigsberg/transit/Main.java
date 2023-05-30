package kenigsberg.transit;

public class Main {
    public static void main(String[] args) {
        TransitComponent component = DaggerTransitComponent
                .builder()
                .build();

        TransitFrame frame = component.providesTransitFrame();
        frame.setVisible(true);
    }
}
