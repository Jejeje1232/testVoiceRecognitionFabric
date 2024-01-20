package mod.jeje.voicerecognition.screen;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon;
import mod.jeje.voicerecognition.voskTest.voskTest;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static mod.jeje.voicerecognition.Jeje_voiceRecognition.MOD_ID;

public class infoScreen extends LightweightGuiDescription {
    public infoScreen() {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(300, 200);
        root.setGaps(0, 1);
        root.setInsets(Insets.ROOT_PANEL);


        WSprite icon = new WSprite(new Identifier(MOD_ID, "icon.png"));
        root.add(icon, 0, 0, 1, 1);

        WLabel label = new WLabel(Text.translatable("gui.jejeVoice.bannedWords"));
        root.add(label, 0, 1, 2, 1);

        //The actual Banned Words text.
        String theString = voskTest.bannedWords.toString();
        String trimmedString = theString.substring(1, theString.length()-1);
        root.add(new WText(Text.of(trimmedString)), 0, 2, 20, 10);

        root.validate(this);
    }
}
