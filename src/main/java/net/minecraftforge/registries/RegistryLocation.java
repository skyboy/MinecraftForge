package net.minecraftforge.registries;

import net.minecraft.util.ResourceLocation;

/* package-private */ class RegistryLocation extends ResourceLocation
{
    private final String[] sortBefore, sortAfter;

    public RegistryLocation(ResourceLocation source, String[] before, String[] after)
    {
        super(0, source.getResourceDomain(), source.getResourcePath());
        sortBefore = before;
        sortAfter = after;
    }

    RegistryLocation(String sortingRule)
    {
        super(0, ":::" + sortingRule, "*");
        sortBefore = new String[0];
        sortAfter = new String[0];
    }

    public String[] getDependants()
    {
        return sortBefore;
    }

    public String[] getDependencies()
    {
        return sortAfter;
    }
}
