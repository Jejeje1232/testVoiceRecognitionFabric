package mod.jeje.voicerecognition.custom;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;

public class JejeDamageSource extends DamageSources {
    public JejeDamageSource(DynamicRegistryManager registryManager) {
        super(registryManager);
    }

    public DamageSource getWither(){
        return this.wither();
    }
}
