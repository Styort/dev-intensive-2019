package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.media.Image
import android.util.AttributeSet
import android.widget.ImageView
import ru.skillbranch.devintensive.R

class AspectRatioImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attributeSet, defStyleAttr){

    companion object{
        private const val DEFAULT_SPECT_RATIO = 1.78f // 16:9
    }

    private var aspectRatio = DEFAULT_SPECT_RATIO

    init{
        if(attributeSet != null){
            val a = context.obtainStyledAttributes(attributeSet, R.styleable.AspectRatioImageView)
            aspectRatio = a.getFloat(R.styleable.AspectRatioImageView_aspectRatio, DEFAULT_SPECT_RATIO)
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // вычисляем ширину и относительно нее рассчитали размер высоты
        val newHeight = (measuredWidth/aspectRatio).toInt()
        setMeasuredDimension(measuredWidth, newHeight)
    }
}