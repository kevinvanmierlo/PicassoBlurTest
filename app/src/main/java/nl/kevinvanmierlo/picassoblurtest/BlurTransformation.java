package nl.kevinvanmierlo.picassoblurtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;

import com.squareup.picasso.Transformation;

/**
 * Created by kevinvanmierlo on 24-04-15.
 */
public class BlurTransformation implements Transformation
{
    private final float BITMAP_SCALE = 0.4f;
    private final float BLUR_RADIUS = 25f;

    private RenderScript rs;

    public BlurTransformation(Context context)
    {
        super();
        rs = RenderScript.create(context);
    }

    @Override
    public Bitmap transform(Bitmap bitmap)
    {
        int width = Math.round(bitmap.getWidth() * BITMAP_SCALE);
        int height = Math.round(bitmap.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        // Allocate memory for Renderscript to work with
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);

        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        script.setRadius(BLUR_RADIUS);
        script.setInput(tmpIn);
        script.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        bitmap.recycle();
        inputBitmap.recycle();

        return outputBitmap;
    }

    @Override
    public String key()
    {
        return "blur";
    }
}
