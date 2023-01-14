package tetrago.caelum.common.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

public class ArcFurnaceRecipe implements Recipe<SimpleContainer>
{
    private final ResourceLocation id;
    private final ItemStack result;
    private final Ingredient ingredient;

    public ArcFurnaceRecipe(ResourceLocation id, ItemStack result, Ingredient ingredient)
    {
        this.id = id;
        this.result = result;
        this.ingredient = ingredient;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel)
    {
        return ingredient.test(pContainer.getItem(0));
    }

    @Override
    public NonNullList<Ingredient> getIngredients()
    {
        return NonNullList.of(Ingredient.EMPTY, ingredient);
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer)
    {
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight)
    {
        return true;
    }

    @Override
    public ItemStack getResultItem()
    {
        return result.copy();
    }

    @Override
    public ResourceLocation getId()
    {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType()
    {
        return Type.INSTANCE;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ArcFurnaceRecipe>
    {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public ArcFurnaceRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe)
        {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "ingredient"));
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));

            return new ArcFurnaceRecipe(pRecipeId, result, ingredient);
        }

        @Nullable
        @Override
        public ArcFurnaceRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer)
        {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            ItemStack result = pBuffer.readItem();

            return new ArcFurnaceRecipe(pRecipeId, result, ingredient);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, ArcFurnaceRecipe pRecipe)
        {
            pRecipe.ingredient.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.result);
        }
    }

    public static class Type implements RecipeType<ArcFurnaceRecipe>
    {
        private Type() {}

        public static final Type INSTANCE = new Type();
        public static final String ID = "arc_furnace";
    }
}
