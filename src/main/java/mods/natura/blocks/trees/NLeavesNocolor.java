package mods.natura.blocks.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.Natura;
import mods.natura.common.NContent;
import net.minecraftforge.common.util.ForgeDirection;

public class NLeavesNocolor extends NLeaves {

    public NLeavesNocolor() {
        super();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBlockColor() {
        return 16777215;
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int par1) {
        return 16777215;
    }

    /**
     * Returns an integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        return 16777215;
    }

    public int damageDropped(int meta) {
        if (meta % 4 == 3) return 4;
        return (meta & 3) + 3;
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        if (meta % 4 == 3) return Item.getItemFromBlock(NContent.rareSapling);
        return Item.getItemFromBlock(NContent.floraSapling);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = super.getDrops(world, x, y, z, metadata, fortune);

        if (metadata % 4 == 2) {
            if (fortune > 3 || Natura.random.nextInt(40 - fortune * 10) == 0) {
                ret.add(new ItemStack(Items.redstone));
            }
        }

        return ret;
    }

    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        int metadata = world.getBlockMetadata(x, y, z);
        if (metadata == 1 || metadata == 2) return 0;
        return Blocks.fire.getFlammability(this);
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        int metadata = world.getBlockMetadata(x, y, z);
        if (metadata == 1 || metadata == 2) return 0;
        return Blocks.fire.getEncouragement(this);
    }

    @Override
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        int metadata = world.getBlockMetadata(x, y, z);
        if (metadata == 1 || metadata == 2) return false;
        return getFlammability(world, x, y, z, face) > 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        final String[] textureNames = new String[] { "sakura", "ghostwood", "bloodwood", "willow" };
        field_150129_M[0] = new IIcon[textureNames.length];
        field_150129_M[1] = new IIcon[textureNames.length];
        for (int i = 0; i < textureNames.length; ++i) {
            field_150129_M[0][i] = iconRegister.registerIcon("natura:" + textureNames[i] + "_leaves_fancy");
            field_150129_M[1][i] = iconRegister.registerIcon("natura:" + textureNames[i] + "_leaves_fast");
        }
    }

    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
    }

}
