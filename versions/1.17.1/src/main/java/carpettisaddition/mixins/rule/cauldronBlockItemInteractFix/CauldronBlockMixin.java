package carpettisaddition.mixins.rule.cauldronBlockItemInteractFix;

import carpettisaddition.utils.ModIds;
import carpettisaddition.utils.compat.DummyClass;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;

@Restriction(require = @Condition(value = ModIds.minecraft, versionPredicates = "<1.17"))
@Mixin(DummyClass.class)
public abstract class CauldronBlockMixin
{
	// no more cauldronBlockItemInteractFix in 1.17+
}