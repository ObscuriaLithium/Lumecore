package com.obscuria.lumecore.world.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.obscuria.lumecore.LumecoreMod;
import com.obscuria.lumecore.system.MansionImmunity;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@MansionImmunity
public class TwistedDagger extends SwordItem {
    public TwistedDagger() {
        super(Tiers.IRON, 2, -2F, new Properties().tab(LumecoreMod.TAB));
    }

    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        final Multimap<Attribute, AttributeModifier> multimap = super.getDefaultAttributeModifiers(slot);
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(multimap);
            builder.put(ObscureAPIAttributes.PENETRATION.get(), new AttributeModifier(UUID.fromString("AB1F55D3-646C-4A28-A497-1C13A33DB5CF"),
                    "Weapon modifier", 0.3, AttributeModifier.Operation.MULTIPLY_BASE));
            return builder.build();
        }
        return multimap;
    }
}
