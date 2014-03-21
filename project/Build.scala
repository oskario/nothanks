import sbt._
import Keys._
import play.Project._
import com.tuplejump.sbt.yeoman.Yeoman

object ApplicationBuild extends Build {

    val appName         = "NoThanks"
    val appVersion      = "0.1"

	val appDependencies = Seq(
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
	    // Add your own project settings here
	    Yeoman.yeomanSettings : _*
	)

}



