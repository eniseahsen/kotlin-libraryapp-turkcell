package com.turkcell.libraryapp.data.repository

import com.turkcell.libraryapp.data.model.BorrowRecord
import com.turkcell.libraryapp.data.supabase.supabase
import io.github.jan.supabase.postgrest.from


class BorrowRepository {

    suspend fun getActiveBorrowByUser(userId: String): Result<List<BorrowRecord>> {
        return runCatching {
            supabase
                .from("borrow_records")
                .select {
                    filter {
                        eq("student_id", userId)

                    }
                }
                .decodeList<BorrowRecord>()
        }
    }

    suspend fun borrowBook(record: BorrowRecord): Result<Unit> = runCatching {
        supabase.from("borrow_records")
            .insert(record)
    }

    suspend fun returnBook(bookId: String, userId: String): Result<Unit> = runCatching {
        supabase.from("borrow_records")
            .update(
                mapOf("returned_at" to "now()")
            ) {
                filter {
                    eq("book_id", bookId)
                    eq("student_id", userId)

                }
            }
    }
}