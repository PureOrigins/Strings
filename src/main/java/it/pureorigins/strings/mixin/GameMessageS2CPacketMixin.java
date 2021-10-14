package it.pureorigins.strings.mixin;

import it.pureorigins.strings.Strings;
import it.pureorigins.framework.configuration.TemplateKt;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Mixin(GameMessageS2CPacket.class)
public class GameMessageS2CPacketMixin {
  @Mutable @Shadow @Final private Text message;
  
  @Inject(at = @At(value = "TAIL"), method = "<init>(Lnet/minecraft/text/Text;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V")
  private void init(Text message, MessageType location, UUID sender, CallbackInfo callback) {
    if (message instanceof TranslatableText) {
      var replace = Strings.INSTANCE.getStrings().get(((TranslatableText) message).getKey());
      if (replace != null) {
        var args = ((TranslatableText) message).getArgs();
        for (var i = 0;i < args.length;i++) {
          if (args[i] instanceof Text) {
            args[i] = Text.Serializer.toJsonTree((Text) args[i]);
          }
        }
        this.message = TemplateKt.templateText(replace, Map.of("args", args), Locale.ROOT);
      }
    }
  }
}
