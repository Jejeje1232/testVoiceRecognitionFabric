package mod.jeje.voicerecognition.screen;

import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WLabeledSlider;
import io.github.cottonmc.cotton.gui.widget.WTabPanel;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.github.cottonmc.cotton.gui.widget.data.VerticalAlignment;

import java.util.function.Consumer;
import java.util.stream.IntStream;

public class testGui extends LightweightGuiDescription {
    public testGui(int hori, int vert) {
    var root = new WGridPanel();
    root.setGaps(hori, vert);
    setRootPanel(root);
    root.setInsets(Insets.ROOT_PANEL);

    addBox(root, 0, 0, 2, 1);
    addBox(root, 0, 1, 1, 2);
    addBox(root, 1, 1, 1, 1);
    addBox(root, 1, 2, 1, 1);

    root.validate(this);
}

    void addBox(WGridPanel root, int x, int y, int w, int h) {
        var l = new WLabel(Text.literal(w + "x" + h), 0xff00ffff);
        l.setVerticalAlignment(VerticalAlignment.CENTER);
        l.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(l, x, y, w, h);
    }
}
