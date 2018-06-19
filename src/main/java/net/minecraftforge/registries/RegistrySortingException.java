/*
 * Minecraft Forge
 * Copyright (c) 2016.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.minecraftforge.registries;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.GuiSortingProblem;
import net.minecraftforge.fml.client.IDisplayableError;
import net.minecraftforge.fml.common.toposort.ModSortingException;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

public class RegistrySortingException extends ModSortingException implements IDisplayableError
{
	private static final long serialVersionUID = 1L;

	public <T> RegistrySortingException(ModSortingException e)
	{
		super(e.getMessage(), e.getExceptionData().getFirstBadNode(), e.getExceptionData().getVisitedNodes());
	}

	@Override
	protected void printStackTrace(WrappedPrintStream stream)
	{
		SortingExceptionData<GameData.RegistryHolder> exceptionData = getExceptionData();
		stream.println("A dependency cycle was detected in the registry set so an ordering cannot be determined");
		stream.println("The first registry in the cycle is " + exceptionData.getFirstBadNode());
		stream.println("The registry cycle involves:");
		for (GameData.RegistryHolder mc : exceptionData.getVisitedNodes())
		{
			stream.println(String.format("\t%s : before: %s, after: %s", mc.toString(), Arrays.toString(mc.getDependants()), Arrays.toString(mc.getDependencies())));
		}
	}


	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen createGui()
	{
		return new GuiSortingProblem(this);
	}
}
