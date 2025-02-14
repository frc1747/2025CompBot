package frc.robot.util;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SignalsConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

/** Sets motor usage for a Spark Max motor controller */
public class CANSparkMaxUtil {
  public enum Usage {
    kAll,
    kPositionOnly,
    kVelocityOnly,
    kMinimal
  };

  /**
   * This function allows reducing a Spark Max's CAN bus utilization by reducing the periodic status
   * frame period of nonessential frames from 20ms to 500ms.
   *
   * <p>See
   * https://docs.revrobotics.com/sparkmax/operating-modes/control-interfaces#periodic-status-frames
   * for a description of the status frames.
   *
   * @param motor The motor to adjust the status frame periods on.
   * @param usage The status frame feedack to enable. kAll is the default when a CANSparkMax is
   *     constructed.
   * @param enableFollowing Whether to enable motor following.
   */
  public static void setCANSparkMaxBusUsage(SparkMax motor, Usage usage, boolean enableFollowing) {
    //SparkMaxConfig config = new SparkMaxConfig();
    SignalsConfig config = new SignalsConfig();

    if (enableFollowing) {
      config
        .appliedOutputPeriodMs(10);
    } else {
      config
        .appliedOutputPeriodMs(500);
    }

    if (usage == Usage.kAll) {
      config
        .primaryEncoderVelocityPeriodMs(20)
        .primaryEncoderPositionPeriodMs(20)
        .analogVoltagePeriodMs(50);
    } else if (usage == Usage.kPositionOnly) {
      config
        .primaryEncoderVelocityPeriodMs(500)
        .primaryEncoderPositionPeriodMs(20)
        .analogVoltagePeriodMs(500);
    } else if (usage == Usage.kVelocityOnly) {
      config
        .primaryEncoderVelocityPeriodMs(20)
        .primaryEncoderPositionPeriodMs(500)
        .analogVoltagePeriodMs(500);
    } else if (usage == Usage.kMinimal) {
      config
        .primaryEncoderVelocityPeriodMs(500)
        .primaryEncoderPositionPeriodMs(500)
        .analogVoltagePeriodMs(500);
    }
    motor.configure(new SparkMaxConfig().apply(config), ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  /**
   * This function allows reducing a Spark Max's CAN bus utilization by reducing the periodic status
   * frame period of nonessential frames from 20ms to 500ms.
   *
   * <p>See
   * https://docs.revrobotics.com/sparkmax/operating-modes/control-interfaces#periodic-status-frames
   * for a description of the status frames.
   *
   * @param motor The motor to adjust the status frame periods on.
   * @param usage The status frame feedack to enable. kAll is the default when a CANSparkMax is
   *     constructed.
   */
  public static void setCANSparkMaxBusUsage(SparkMax motor, Usage usage) {
    setCANSparkMaxBusUsage(motor, usage, false);
  }
}