package com.example.w6.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.w6.model.Expense

class ExpensesViewModel : ViewModel() {

    var expenses by mutableStateOf<List<Expense>>(emptyList())
        private set

    var expenseName by mutableStateOf("")
        private set

    var expenseAmount by mutableStateOf("")
        private set

    var errorMessage by mutableStateOf("")
        private set

    val totalExpenses: Long
        get() = expenses.sumOf { it.amount }

    private var nextId = 1

    fun onNameChange(value: String) {
        expenseName = value
        errorMessage = ""
    }

    fun onAmountChange(value: String) {
        expenseAmount = value
        errorMessage = ""
    }

    fun addExpense() {
        val name = expenseName.trim()
        val amount = expenseAmount.toLongOrNull()

        when {
            name.isEmpty() -> {
                errorMessage = "Expense name cannot be empty"
            }

            amount == null -> {
                errorMessage = "Please enter a valid amount"
            }

            amount <= 0L -> {
                errorMessage = "Amount must be greater than 0"
            }

            else -> {
                expenses = expenses + Expense(
                    id = nextId++,
                    name = name,
                    amount = amount
                )

                expenseName = ""
                expenseAmount = ""
                errorMessage = ""
            }
        }
    }

    fun deleteExpense(id: Int) {
        expenses = expenses.filterNot { it.id == id }
        errorMessage = ""
    }
}
