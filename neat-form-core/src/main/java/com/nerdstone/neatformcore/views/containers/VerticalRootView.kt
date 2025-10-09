package com.nerdstone.neatformcore.views.containers

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nerdstone.neatformcore.domain.builders.FormBuilder
import com.nerdstone.neatformcore.domain.model.NFormViewProperty
import com.nerdstone.neatformcore.domain.view.NFormView
import com.nerdstone.neatformcore.domain.view.RootView
import com.nerdstone.neatformcore.utils.createViews
import com.nerdstone.neatformcore.utils.pxToDp

class VerticalRootView : LinearLayout, RootView {

    override lateinit var formBuilder: FormBuilder
    private var insetsApplied = false
    private var baseStart = 0
    private var baseTop = 0
    private var baseEnd = 0
    private var baseBottom = 0

    init {
        orientation = VERTICAL
        // Apply system bar insets once to avoid crowding status/nav bars
        baseStart = paddingLeft
        baseTop = paddingTop
        baseEnd = paddingRight
        baseBottom = paddingBottom
        ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
            if (!insetsApplied) {
                val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(baseStart, baseTop + bars.top, baseEnd, baseBottom + bars.bottom)
                insetsApplied = true
            }
            insets
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun initRootView(formBuilder: FormBuilder): RootView {
        this.formBuilder = formBuilder
        return this
    }

    override fun addChild(nFormView: NFormView) {
        val view = nFormView.viewDetails.view
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        params.bottomMargin = context.pxToDp(16f)
        params.topMargin = context.pxToDp(8f)
        params.marginStart = context.pxToDp(16f)
        params.marginEnd = context.pxToDp(16f)
        view.layoutParams = params
        this.addView(view)
    }

    override fun addChildren(
        viewProperties: List<NFormViewProperty>, formBuilder: FormBuilder, buildFromLayout: Boolean
    ) = createViews(viewProperties, formBuilder, buildFromLayout)
}
