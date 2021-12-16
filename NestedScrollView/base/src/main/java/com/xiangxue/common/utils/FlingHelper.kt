package com.xiangxue.common.utils

import android.content.Context
import android.view.ViewConfiguration
import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.ln

class FlingHelper(context: Context) {

    // 物理系数
    private var mPhysicalCoefficient: Float =
        context.resources.displayMetrics.density * 160.0f * 386.0878f * 0.84f

    // 减速率
    private val mDecelerationRate = (ln(0.78) / ln(0.9)).toFloat()

    // 摩擦量
    private val mFlingFriction = ViewConfiguration.getScrollFriction()

    private fun getSplineDeceleration(i: Int): Double =
        ln((0.35f * abs(i).toFloat() / (mFlingFriction * mPhysicalCoefficient)).toDouble())

    private fun getSplineDecelerationByDistance(d: Double): Double =
        (mDecelerationRate.toDouble() - 1.0) * ln(d / (mFlingFriction * mPhysicalCoefficient).toDouble()) / mDecelerationRate.toDouble()

    /**
     * 通过速度获取样条曲线移动距离
     */
    fun getSplineFlingDistance(i: Int): Double =
        exp(getSplineDeceleration(i) * (mDecelerationRate.toDouble() / (mDecelerationRate.toDouble() - 1.0))) * (mFlingFriction * mPhysicalCoefficient).toDouble()

    /**
     * 通过距离获得速度
     */
    fun getVelocityByDistance(d: Double): Int =
        abs((exp(getSplineDecelerationByDistance(d)) * mFlingFriction.toDouble() * mPhysicalCoefficient.toDouble() / 0.3499999940395355).toInt())

}