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

import net.minecraftforge.fml.common.EnhancedRuntimeException;
import net.minecraftforge.fml.common.toposort.ModSortingException;
import net.minecraftforge.fml.common.toposort.ModSortingException.SortingExceptionData;

import java.util.Arrays;

public class RegistrySortingException extends EnhancedRuntimeException
{
	private static final long serialVersionUID = 1L;

	private SortingExceptionData<?> sortingExceptionData;

	public <T> RegistrySortingException(ModSortingException e)
	{
		super(e.getMessage());
		sortingExceptionData =e.getExceptionData();
	}

	@SuppressWarnings("unchecked")
	public <T> SortingExceptionData<T> getExceptionData()
	{
		return (SortingExceptionData<T>) sortingExceptionData;
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
}
