package mod.jeje.voicerecognition.test;

import static mod.jeje.voicerecognition.constants.NOTIF_TIME;
import static mod.jeje.voicerecognition.utils.utilStructures.ARGB;
import static mod.jeje.voicerecognition.utils.utilStructures.TextPool;

import lombok.Setter;
import mod.jeje.voicerecognition.utils.utilStructures;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class testGuiNotifier implements HudRenderCallback {

    private static ARGB currentARGB = new ARGB(0xFF, 0xFF, 0xFF, 0xFF);
    private static int color = 0xFFFFFFFF;
    private static int tempAlpha;
    @Setter
    private static String text = "";
    private static TextPool textPool = new TextPool(testGuiNotifier::renderText);
    private static float test = 0f;
    private static boolean toTest = false;
    private static float unitTime = 1f;

    public static void setColor(int Alpha, int Red, int Green, int Blue){
        color = (Alpha << 24) | (Red << 16) | (Green << 8) | Blue;
        currentARGB.Alpha = Alpha;
        currentARGB.Red = Red;
        currentARGB.Green = Green;
        currentARGB.Blue = Blue;
    }

    public static void queueText(String theText){
        textPool.add(theText);
    }

    private static void renderText(String theText){
        text = theText;
        toTest = true;
    }

    private static boolean updateTime(float tickDelta){
        if (toTest){
            //The check is like this because: 1 fade in and 1 fade out, the rest is on-screen time.
            if (test < NOTIF_TIME + 2){
                test += tickDelta/25;
                //unitTime = (test > 1 && test < 2) ? 1f : ((test > 3) ? ((unitTime < 0) ? 0f : 3-test) : test);
                unitTime = (test < 1) ? (test) : ((test < NOTIF_TIME+1) ? 1f : ((test < NOTIF_TIME+2) ? (NOTIF_TIME+2-test) : 0f));

                //System.out.println((int) ((tempAlpha * unitTime < 10) ? 10f : (tempAlpha * unitTime)));
                setColor((int) ((tempAlpha * unitTime < 10) ? 10f : (tempAlpha * unitTime)), currentARGB.Red, currentARGB.Green, currentARGB.Blue);
                return false;
            }
            test = 0f;
            text = "";
            unitTime = 1f;
            currentARGB.Alpha = tempAlpha;
            return true;
        }
        return false;
    }

    private static boolean someSetups(){
        tempAlpha = currentARGB.Alpha;
        if (textPool.isEmpty()){return false;}
        textPool.next();
        return true;
    }


    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        TextRenderer renderer = MinecraftClient.getInstance().textRenderer;

        //textPool.test();
        toTest = toTest ? !updateTime(tickDelta) : someSetups();

        int x = (drawContext.getScaledWindowWidth()/2) - (renderer.getWidth(text)/2);
        int relY = (int) (drawContext.getScaledWindowHeight()/20);
        int y = (int) ( relY * (1 - Math.pow(1 - unitTime, 3)));

        drawContext.drawTextWithShadow(renderer, text, x, y, color);
    }
}
