package mods.natura.blocks.trees;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.common.NaturaTab;
import mods.natura.util.Util;

public class TreeBlock extends BlockLog {

    public IIcon[] icons;
    public String[] textureNames = new String[] { "eucalyptus_bark", "sakura_bark", "ghostwood_bark", "hopseed_bark",
            "eucalyptus_heart", "sakura_heart", "ghostwood_heart", "hopseed_heart" };

    public TreeBlock() {
        super();
        this.setHardness(1.5F);
        this.setResistance(5F);
        this.setStepSound(Block.soundTypeWood);
        this.setCreativeTab(NaturaTab.tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata) {
        int tex = (metadata % 4);
        int orientation = metadata / 4;

        switch (orientation)
        // Ends of logs
        {
            case 0:
                if (side == 0 || side == 1) {
                    return Util.getWithFallback(icons, tex + 4);
                }
                break;
            case 1:
                if (side == 4 || side == 5) {
                    return Util.getWithFallback(icons, tex + 4);
                }
                break;
            case 2:
                if (side == 2 || side == 3) {
                    return Util.getWithFallback(icons, tex + 4);
                }
                break;
        }

        return Util.getWithFallback(icons, tex);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister IIconRegister) {
        this.icons = new IIcon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i) {
            this.icons[i] = IIconRegister.registerIcon("natura:" + textureNames[i]);
        }
    }

    @Override
    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public int damageDropped(int meta) {
        return meta % 4;
    }

    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        int metadata = world.getBlockMetadata(x, y, z);
        if (metadata % 4 == 2) return 0;
        return Blocks.fire.getFlammability(this);
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        int metadata = world.getBlockMetadata(x, y, z);
        if (metadata % 4 == 2) return 0;
        return Blocks.fire.getEncouragement(this);
    }

    @Override
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        int metadata = world.getBlockMetadata(x, y, z);
        if (metadata % 4 == 2) return false;
        return getFlammability(world, x, y, z, face) > 0;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int i = 0; i < 4; i++) par3List.add(new ItemStack(par1, 1, i));
    }
}
