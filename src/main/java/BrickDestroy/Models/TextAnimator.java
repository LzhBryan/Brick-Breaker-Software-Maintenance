package BrickDestroy.Models;

import java.util.Random;

/**
 * A class to run the text animation.
 */
public class TextAnimator implements Runnable {

    private String text;
    private int animationTime;
    private TextOutput textOutput;
    private Random random = new Random();

    /**
     * Constructor to initialize.
     * @param text text to be displayed.
     * @param animationTime the animation time.
     * @param textField the text field.
     */
    public TextAnimator(String text, int animationTime, TextOutput textField) {
        this.text = text;
        this.animationTime = animationTime;
        this.textOutput = textField;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i <= text.length(); i++) {
                String textAtThisPoint = text.substring(0,i);
                textOutput.writeText(textAtThisPoint);
                Thread.sleep(animationTime + random.nextInt(150));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
