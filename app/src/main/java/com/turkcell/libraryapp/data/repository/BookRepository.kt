package com.turkcell.libraryapp.data.repository

import com.turkcell.libraryapp.data.model.Book
import com.turkcell.libraryapp.data.supabase.supabase
import io.github.jan.supabase.postgrest.postgrest

public class BookRepository {
    suspend fun getAllBooks(): Result<List<Book>> = runCatching {
        supabase.postgrest["books"]
            .select()
            .decodeList<Book>()
    }

    suspend fun getBookById(id: String): Result<Book> = runCatching {
        supabase.postgrest["books"]
            .select { filter { eq("id",id) } }
            .decodeSingle<Book>()
    }

    suspend fun addBook(book: Book): Result<Unit> = runCatching {
        supabase.postgrest["books"].insert(book)
    }

    suspend fun updateBook(book: Book): Result<Unit> = runCatching {
        supabase.postgrest["books"]
            .update(book){
                filter{
                    eq("id",book.id)
                }
            }
    }

    suspend fun deleteBook(id: String): Result<Unit> = runCatching {
        supabase.postgrest["books"]
            .delete{
                filter{
                    eq("id",id)
                }
            }
    }

    //bu fonksiyonlar ağ (network) isteği yapıyor ve Kotlin’de böyle işlemler ana thread’i bloklamadan çalıştırılmalı
    suspend fun searchBooks(query: String): Result<List<Book>> =runCatching {
        supabase.postgrest["books"]
            .select {
                filter{
                    or{
                        ilike("title","%$query%")
                        ilike("author","%$query%")
                    }

                }
            }
            .decodeList<Book>()
    }
}