package ValkyrienWarfareBase.CoreMod;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import code.elix_x.excomms.asm.transform.ASMTransformer;
import code.elix_x.excomms.asm.transform.InsnListBuilder;
import code.elix_x.excomms.asm.transform.children.specific.SpecificClassNodeChildrenTransformer;
import code.elix_x.excomms.asm.transform.specific.SpecificMethodNodeTransformer;
import net.minecraft.launchwrapper.IClassTransformer;

public class ValkyrienWarfareTransformer implements IClassTransformer {

	private ASMTransformer transformer = new ASMTransformer(
			new SpecificClassNodeChildrenTransformer.Builder("net/minecraftforge/common/ForgeChunkManager", 0)
			.node(SpecificMethodNodeTransformer.instructionsBeginningInserter("getPersistentChunksIterableFor", 0, new InsnListBuilder(new VarInsnNode(ALOAD, 1), new MethodInsnNode(INVOKESTATIC, ValkyrienWarfarePlugin.PathCommon, "rebuildChunkIterator", "(Ljava/util/Iterator;)Ljava/util/Iterator;", false), new VarInsnNode(ASTORE, 1), new LabelNode()).build()))
			.build(),
			new SpecificClassNodeChildrenTransformer.Builder("net/minecraft/entity/player/EntityPlayer", 0)
			.node(SpecificMethodNodeTransformer.instructionsInserterBeforeReturn(ValkyrienWarfarePlugin.isObfuscatedEnvironment ? "func_180467_a" : "getBedSpawnLocation", 0, () -> new InsnListBuilder(new VarInsnNode(ALOAD, 0), new VarInsnNode(ALOAD, 1), new VarInsnNode(ILOAD, 2), new MethodInsnNode(INVOKESTATIC, ValkyrienWarfarePlugin.PathCommon, "getBedSpawnLocation", "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Z)Lnet/minecraft/util/math/BlockPos;", false)).build()))
			.node(SpecificMethodNodeTransformer.instructionsInserterBeforeReturn(ValkyrienWarfarePlugin.isObfuscatedEnvironment ? "func_70999_a" : "wakeUpPlayer", 0, () -> new InsnListBuilder(new VarInsnNode(ALOAD, 0), new MethodInsnNode(INVOKESTATIC, ValkyrienWarfarePlugin.PathCommon, "afterWakeUpPlayer", "(Lnet/minecraft/entity/player/EntityPlayer;)V", false)).build()))
			.node(SpecificMethodNodeTransformer.instructionsInserterBeforeReturn(ValkyrienWarfarePlugin.isObfuscatedEnvironment ? "func_180469_a" : "trySleep", 0, () -> new InsnListBuilder(new VarInsnNode(ALOAD, 0), new VarInsnNode(ALOAD, 1), new MethodInsnNode(INVOKESTATIC, ValkyrienWarfarePlugin.PathCommon, "trySleep", "(Lnet/minecraft/entity/player/EntityPlayer$SleepResult;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/entity/player/EntityPlayer$SleepResult;", false)).build()))
			.build(),
			new SpecificClassNodeChildrenTransformer.Builder("net/minecraft/tileentity/TileEntity", 0)
			.node(SpecificMethodNodeTransformer.instructionsInserterBeforeReturn(ValkyrienWarfarePlugin.isObfuscatedEnvironment ? "func_145835_a" : "getDistanceSq", 0, () -> new InsnListBuilder(new VarInsnNode(ALOAD, 0), new VarInsnNode(DLOAD, 1), new VarInsnNode(DLOAD, 2), new VarInsnNode(DLOAD, 3), new MethodInsnNode(INVOKESTATIC, ValkyrienWarfarePlugin.PathCommon, "getDistanceSq", "getDistanceSq(DLnet/minecraft/tileentity/TileEntity;DDD)D", false)).build()))
			.build(),
			new SpecificClassNodeChildrenTransformer.Builder("net/minecraft/entity/Entity", 0)
			.node(SpecificMethodNodeTransformer.instructionsInserterBeforeReturn(ValkyrienWarfarePlugin.isObfuscatedEnvironment ? "func_70092_e(DDD)D" : "getDistanceSq(DDD)D", 0, () -> new InsnListBuilder(new VarInsnNode(ALOAD, 0), new VarInsnNode(DLOAD, 1), new VarInsnNode(DLOAD, 2), new VarInsnNode(DLOAD, 3), new MethodInsnNode(INVOKESTATIC, ValkyrienWarfarePlugin.PathCommon, "getDistanceSq", "getDistanceSq(DLnet/minecraft/entity/Entity;DDD)D", false)).build()))
			.build());

	@Override
	public byte[] transform(String name, String transformedName, byte[] classData){
		classData = ASMUtils.doAdditionalTransforms(transformedName, transformer.transform(classData));

		TransformAdapter adapter = new TransformAdapter(ASM5, ValkyrienWarfarePlugin.isObfuscatedEnvironment, transformedName);
		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		adapter.setCV(classWriter);
		try {
			new ClassReader(classData).accept(adapter, ClassReader.EXPAND_FRAMES);
			 classData  = classWriter.toByteArray();

			//Performs sanity checks and frame stack recalculations before pushing the new bytecode
//			ClassReader cr = new ClassReader(byteArray);
//			ClassWriter checkedWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
//			CheckClassAdapter adapterChecker = new CheckClassAdapter(checkedWriter, true);
//			cr.accept(adapterChecker, ClassReader.EXPAND_FRAMES);
//			return checkedWriter.toByteArray();
		} catch (Exception e) {
//			System.out.println(transformedName);
			e.printStackTrace();
		}
		return classData;
	}

}