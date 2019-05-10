package com.noobanidus.nvg.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelGoggles extends ModelBiped {
    public static final ModelGoggles model_goggles = new ModelGoggles();
    public static final ModelGoggles model_goggles_bauble = new ModelGoggles();

    public ModelRenderer goggles;

    public ModelGoggles() {
        this.textureHeight = 32;
        this.textureWidth = 64;

        this.goggles = new ModelRenderer(this, 0, 0);
        this.goggles.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.goggles.cubeList.add(new ModelGogglesBox(this.goggles, 0, 0, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.75f, false, 16, 16, 16));
        bipedHead = this.goggles;
        bipedHeadwear.showModel = false;
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        bipedHead = this.goggles;
        bipedHeadwear.showModel = false;

        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    }

    // new ModelCustomUVBox(sunglasses, )
    public static class ModelGogglesBox extends ModelBox {
        public TexturedQuad[] quads;

        public ModelGogglesBox(ModelRenderer renderer, int U, int V, float x, float y, float z, float dx, float dy, float dz, float scale, boolean mirror, int Dx, int Dy, int Dz) {
            super(renderer, U, V, x, y, z, (int) dx, (int) dy, (int) dz, scale, mirror);

            quads = new TexturedQuad[6];

            float f = x + dx;
            float f1 = y + dy;
            float f2 = z + dz;
            x -= scale;
            y -= scale;
            z -= scale;
            f += scale;
            f1 += scale;
            f2 += scale;

            if (mirror) {
                float f3 = f;
                f = x;
                x = f3;
            }

            PositionTextureVertex vertex0 = new PositionTextureVertex(f, y, z, 0.0F, 8.0F);
            PositionTextureVertex vertex1 = new PositionTextureVertex(f, f1, z, 8.0F, 8.0F);
            PositionTextureVertex vertex2 = new PositionTextureVertex(x, f1, z, 8.0F, 0.0F);
            PositionTextureVertex vertex3 = new PositionTextureVertex(x, y, f2, 0.0F, 0.0F);
            PositionTextureVertex vertex4 = new PositionTextureVertex(f, y, f2, 0.0F, 8.0F);
            PositionTextureVertex vertex5 = new PositionTextureVertex(f, f1, f2, 8.0F, 8.0F);
            PositionTextureVertex vertex6 = new PositionTextureVertex(x, f1, f2, 8.0F, 0.0F);
            PositionTextureVertex vertex7 = new PositionTextureVertex(x, y, z, 0.0F, 0.0F);

            this.quads[0] = new TexturedQuad(new PositionTextureVertex[]{vertex4, vertex0, vertex1, vertex5}, U + Dz + Dx, V + Dz, U + Dz + Dx + Dz, V + Dz + Dy, renderer.textureWidth, renderer.textureHeight);
            this.quads[1] = new TexturedQuad(new PositionTextureVertex[]{vertex7, vertex3, vertex6, vertex2}, U, V + Dz, U + Dz, V + Dz + Dy, renderer.textureWidth, renderer.textureHeight);
            this.quads[2] = new TexturedQuad(new PositionTextureVertex[]{vertex4, vertex3, vertex7, vertex0}, U + Dz, V, U + Dz + Dx, V + Dz, renderer.textureWidth, renderer.textureHeight);
            this.quads[3] = new TexturedQuad(new PositionTextureVertex[]{vertex1, vertex2, vertex6, vertex5}, U + Dz + Dx, V + Dz, U + Dz + Dx + Dx, V, renderer.textureWidth, renderer.textureHeight);
            this.quads[4] = new TexturedQuad(new PositionTextureVertex[]{vertex0, vertex7, vertex2, vertex1}, U + Dz, V + Dz, U + Dz + Dx, V + Dz + Dy, renderer.textureWidth, renderer.textureHeight);
            this.quads[5] = new TexturedQuad(new PositionTextureVertex[]{vertex3, vertex4, vertex5, vertex6}, U + Dz + Dx + Dz, V + Dz, U + Dz + Dx + Dz + Dx, V + Dz + Dy, renderer.textureWidth, renderer.textureHeight);

            if (mirror) {
                for (TexturedQuad quad : this.quads) {
                    quad.flipFace();
                }
            }
        }

        @SideOnly(Side.CLIENT)
        @Override
        public void render (BufferBuilder renderer, float scale) {
            if (quads == null) {
                super.render(renderer, scale);
                return;
            }
            for (TexturedQuad quad : this.quads) {
                quad.draw(renderer, scale);
            }
        }
    }
}
