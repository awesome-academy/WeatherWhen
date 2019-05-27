package com.thailam.weatherwhen.utils

import android.animation.Animator
import android.view.View
import android.view.ViewAnimationUtils

object MyAnimationUtils {
    fun createCustomCircularRevealAnimation(
        currentView: View,
        revealView: View,
        direction: AnimStartDirection = AnimStartDirection.LEFT_BOTTOM,
        reverse: Boolean = false,
        duration: Long = 500,
        animationStart: () -> Unit = {},
        animationEnd: () -> Unit = {}
    ) {
        var x = 0
        var y = 0
        when (direction) {
            AnimStartDirection.LEFT_BOTTOM -> {
                x = currentView.left
                y = currentView.bottom
            }
        }
        var startRadius = 0.toFloat()
        var endRadius = Math.hypot(currentView.height.toDouble(), currentView.height.toDouble()).toFloat()
        if (reverse) {
            startRadius = endRadius
            endRadius = 0.toFloat()
        }

        val anim: Animator = ViewAnimationUtils
            .createCircularReveal(revealView, x, y, startRadius, endRadius)
            .setAnimationStartEnd({ animationStart() }, { animationEnd() })
        anim.duration = duration
        anim.start()
    }
}

// Extension function for Animator to implement only animation start and end
fun Animator.setAnimationStartEnd(animationStart: () -> Unit, animationEnd: () -> Unit): Animator {
    this.addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onAnimationEnd(animation: Animator?) {
            animationEnd()
        }

        override fun onAnimationCancel(animation: Animator?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onAnimationStart(animation: Animator?) {
            animationStart()
        }
    })
    return this@setAnimationStartEnd
}

enum class AnimStartDirection {
    LEFT_BOTTOM
}
