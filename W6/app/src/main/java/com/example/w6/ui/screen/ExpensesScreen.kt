package com.example.w6.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.w6.viewmodel.ExpensesViewModel
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.ui.tooling.preview.Preview

private val BackgroundColor = Color(0xFFF5F1FA)
private val PrimaryColor = Color(0xFF6650A4)
private val ExpenseCardColor = Color(0xFFDCE8FA)
private val DeleteColor = Color(0xFFD93636)

@Composable
fun ExpensesScreen(
    viewModel: ExpensesViewModel
) {
    val expenses = viewModel.expenses
    val total = viewModel.totalExpenses

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(24.dp)
    ) {
        Text(
            text = "Expenses",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Total Expense : ${formatRupiah(total)}",
            fontSize = 14.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = viewModel.expenseName,
            onValueChange = viewModel::onNameChange,
            label = { Text("Expense Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = viewModel.expenseAmount,
                onValueChange = viewModel::onAmountChange,
                label = { Text("Amount (Rp)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            )

            Button(
                onClick = viewModel::addExpense,
                modifier = Modifier.size(56.dp),
                shape = CircleShape,
                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add expense",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        if (viewModel.errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = viewModel.errorMessage,
                color = DeleteColor,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (expenses.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No expenses yet",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = expenses,
                    key = { it.id }
                ) { expense ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = ExpenseCardColor
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp,
                                    top = 12.dp,
                                    bottom = 12.dp,
                                    end = 8.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Name : ${expense.name}",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF20243A)
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Text(
                                    text = formatRupiah(expense.amount),
                                    fontSize = 15.sp,
                                    color = Color(0xFF394867)
                                )
                            }

                            IconButton(
                                onClick = {
                                    viewModel.deleteExpense(expense.id)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete ${expense.name}",
                                    tint = DeleteColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun formatRupiah(amount: Long): String {
    val formatter = NumberFormat.getCurrencyInstance(
        Locale("id", "ID")
    )
    formatter.maximumFractionDigits = 0
    formatter.minimumFractionDigits = 0
    return formatter.format(amount)
}



@Preview(showBackground = true)
@Composable
fun ExpensesScreenPreview() {
    MaterialTheme {
        ExpensesScreen(
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }
}
