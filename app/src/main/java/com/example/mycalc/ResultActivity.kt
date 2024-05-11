package com.example.mycalc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        // Retrieve score and question data
        val score = intent.getIntExtra("score", 0)
        val totalQuestions = intent.getIntExtra("totalQuestions", 5)
        val correctAnswers = intent.getStringArrayListExtra("correctAnswers") ?: arrayListOf()
        val incorrectAnswers = intent.getStringArrayListExtra("incorrectAnswers") ?: arrayListOf()

        // Find UI elements
        val resultsTextView = findViewById<TextView>(R.id.resultsTextView)
        val feedbackTextView = findViewById<TextView>(R.id.feedbackTextView)
        val correctAnswersTextView = findViewById<TextView>(R.id.correctAnswersTextView)
        val incorrectAnswersTextView = findViewById<TextView>(R.id.incorrectAnswersTextView)
        val redoButton = findViewById<Button>(R.id.redoButton)
        val exitButton = findViewById<Button>(R.id.exitButton)

        // Set text for results and feedback based on the score
        resultsTextView.text = getString(R.string.result_format, score, totalQuestions)
        feedbackTextView.text = when {
            score == totalQuestions -> getString(R.string.feedback_perfect)
            score > totalQuestions / 2 -> getString(R.string.feedback_above_average)
            else -> getString(R.string.feedback_try_again)
        }

        // Display counts and details of correct and incorrect answers
        correctAnswersTextView.text = "Correct Answers (${correctAnswers.size}):\n" + correctAnswers.joinToString("\n")
        incorrectAnswersTextView.text = "Incorrect Answers (${incorrectAnswers.size}):\n" + incorrectAnswers.joinToString("\n")

        redoButton.setOnClickListener {
            // Restart QuestionsActivity
            startActivity(Intent(this, QuestionsActivity::class.java))
            finish()
        }

        exitButton.setOnClickListener {
            // Exit the application
            finishAffinity()
        }
    }
}
