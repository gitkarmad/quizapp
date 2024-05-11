package com.example.mycalc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

data class Question(
    val questionText: String,
    val options: List<String>,
    val correctAnswer: String
)

class QuestionsActivity : AppCompatActivity() {

    private val questions = listOf(
        Question("What is 5 + 3?", listOf("8", "7", "6", "9"), "8"),
        Question("What is 10 - 2?", listOf("7", "8", "6", "9"), "8"),
        Question("What is 3 * 4?", listOf("10", "12", "14", "9"), "12"),
        Question("What is 12 / 3?", listOf("3", "4", "5", "6"), "4"),
        Question("What is 7 + 2?", listOf("9", "8", "6", "10"), "9")
    )

    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questionTextView: TextView
    private lateinit var optionButtons: List<Button>
    private lateinit var resetButton: Button
    private lateinit var showResultsButton: Button
    private lateinit var nextButton: Button
    private val correctAnswers = arrayListOf<String>()
    private val incorrectAnswers = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questionTextView = findViewById(R.id.questionTextView)
        optionButtons = listOf(
            findViewById(R.id.option1),
            findViewById(R.id.option2),
            findViewById(R.id.option3),
            findViewById(R.id.option4)
        )
        resetButton = findViewById(R.id.resetButton)
        showResultsButton = findViewById(R.id.showResultsButton)
        val nextButton: Button = findViewById(R.id.nextButton)
        nextButton.setBackgroundColor(ContextCompat.getColor(this, R.color.button_next))

        setupButtons()
        showQuestion()

        resetButton.setOnClickListener {
            resetQuiz()
        }

        showResultsButton.setOnClickListener {
            showResults()
        }

        nextButton.setOnClickListener {
            moveToNextQuestion()
        }
    }

    private fun setupButtons() {
        optionButtons.forEach { button ->
            button.setOnClickListener {
                handleAnswer(button.text.toString())
            }
        }
    }

    private fun showQuestion() {
        val question = questions.getOrNull(currentQuestionIndex)
        question?.let {
            questionTextView.text = it.questionText
            optionButtons.forEachIndexed { index, button ->
                if (index < it.options.size) {
                    button.text = it.options[index]
                    button.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
                    button.isEnabled = true
                }
            }
        }
    }

    private fun handleAnswer(selectedOption: String) {
        val question = questions[currentQuestionIndex]
        if (selectedOption == question.correctAnswer) {
            score++
            correctAnswers.add(question.questionText)
        } else {
            incorrectAnswers.add(question.questionText)
        }
        optionButtons.forEach { it.isEnabled = false }
    }

    private fun moveToNextQuestion() {
        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            showQuestion()
        } else {
            showResults()
        }
    }

    private fun resetQuiz() {
        currentQuestionIndex = 0
        score = 0
        correctAnswers.clear()
        incorrectAnswers.clear()
        optionButtons.forEach {
            it.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
            it.isEnabled = true
        }
        showQuestion()
    }

    private fun showResults() {
        val intent = Intent(this, ResultsActivity::class.java).apply {
            putExtra("score", score)
            putExtra("totalQuestions", questions.size)
            putStringArrayListExtra("correctAnswers", correctAnswers)
            putStringArrayListExtra("incorrectAnswers", incorrectAnswers)
        }
        startActivity(intent)
        finish()
    }
}
