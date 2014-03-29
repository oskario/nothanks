import sbt._
import Keys._
import play.Project._
import com.tuplejump.sbt.yeoman.Yeoman

object ApplicationBuild extends Build {

  val appName = "NoThanks"
  val appVersion = "0.2"

  val appDependencies = Seq(
    "org.reactivemongo" %% "play2-reactivemongo" % "0.10.2"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
    Yeoman.yeomanSettings: _*
  )

}



