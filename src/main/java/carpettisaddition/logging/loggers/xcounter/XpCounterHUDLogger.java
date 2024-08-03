/*
 * This file is part of the Carpet TIS Addition project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2024  Fallen_Breath and contributors
 *
 * Carpet TIS Addition is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Carpet TIS Addition is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Carpet TIS Addition.  If not, see <https://www.gnu.org/licenses/>.
 */

package carpettisaddition.logging.loggers.xcounter;

import carpettisaddition.commands.common.counter.DyeCounterProvider;
import carpettisaddition.commands.xcounter.XpCounterCommand;
import carpettisaddition.logging.common.counter.DyeCounterHudLogger;

public class XpCounterHUDLogger extends DyeCounterHudLogger
{
	private static final XpCounterHUDLogger INSTANCE = new XpCounterHUDLogger();
	public static final String NAME = "xcounter";

	public XpCounterHUDLogger()
	{
		super(NAME);
	}

	public static XpCounterHUDLogger getInstance()
	{
		return INSTANCE;
	}

	@Override
	protected DyeCounterProvider<?, ?> getCounterProvider()
	{
		return XpCounterCommand.getInstance();
	}
}
