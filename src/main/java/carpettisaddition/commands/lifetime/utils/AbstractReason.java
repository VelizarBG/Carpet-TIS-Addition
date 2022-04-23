package carpettisaddition.commands.lifetime.utils;

import carpettisaddition.commands.lifetime.LifeTimeTracker;
import carpettisaddition.translations.TranslationContext;
import net.minecraft.text.MutableText;

public abstract class AbstractReason extends TranslationContext
{
	public AbstractReason(String reasonType)
	{
		super(LifeTimeTracker.getInstance().getTranslator().getDerivedTranslator(reasonType));
	}

	public abstract MutableText toText();
}
