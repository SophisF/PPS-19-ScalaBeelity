package scala.controller

import scala.controller.IO.unit
import scala.io.StdIn

trait IO[+A] {
  def flatMap[B](f: A => IO[B]): IO[B]

  def map[B](f: A => B): IO[B] = flatMap(x => unit(f(x)))
}

object IO {
  def unit[A](a: A) = new IO[A] { // semantics of ";" after unit
    override def flatMap[B](f: A => IO[B]): IO[B] = f(a)
  }

  def write[A](a: A) = new IO[A] { // semantics of ";" after write
    override def flatMap[B](f: A => IO[B]): IO[B] = {
      println(a); f(a)
    }
  }

  def read() = new IO[String] { // semantics of ";" after read
    override def flatMap[B](f: String => IO[B]): IO[B] = f(StdIn.readLine())
  }
}