package valkyrienwarfare.mixin.world.chunk;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BitArray;
import net.minecraft.world.chunk.BlockStateContainer;
import net.minecraft.world.chunk.IBlockStatePalette;
import valkyrienwarfare.physics.collision.optimization.IBitOctree;
import valkyrienwarfare.physics.collision.optimization.IBitOctreeProvider;
import valkyrienwarfare.physics.collision.optimization.SimpleBitOctree;

@Mixin(BlockStateContainer.class)
public class MixinBlockStateContainer implements IBitOctreeProvider {

    private final IBitOctree bitOctree = new SimpleBitOctree();
    @Shadow
    IBlockStatePalette palette;
    @Shadow
    BitArray storage;

    // I know overwrite is a bit overkill here, but I am avoiding using
    // redirects to prevent CallBack objects from being created. This set() 
    // method is very low level, and creating new objects within it will cause
    // serious performance problems.
    // Also this method is set to protected in the BlockStateContainer class, 
    // but its safer to set it to public to prevent conflicts with access 
    // transformers from other mods.
    // ~Alex Mastrangelo
    @Overwrite
    public void set(int index, IBlockState state) {
        int i = this.palette.idFor(state);
        this.storage.setAt(index, i);
        // VW code starts here:
        int x = index & 0xF;
        int z = (index & 0xF0) >> 4;
        int y = (index & 0xF00) >> 8;
        boolean isStateSolid = state.getMaterial().isSolid();
        bitOctree.set(x & 15, y & 15, z & 15, isStateSolid);
    }

    @Override
    public IBitOctree getBitOctree() {
        return bitOctree;
    }
}