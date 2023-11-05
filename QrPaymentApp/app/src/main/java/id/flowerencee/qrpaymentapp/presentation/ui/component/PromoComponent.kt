package id.flowerencee.qrpaymentapp.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoItem

@Composable
fun promoItemContent(data: PromoItem, onClick: (PromoItem) -> Unit) {
    val cardModifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .wrapContentHeight()
    val cardElevation = CardDefaults.cardElevation(
        defaultElevation = 4.dp
    )
    Card(
        modifier = cardModifier,
        shape = MaterialTheme.shapes.medium,
        elevation = cardElevation
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick(data) }
        ) {
            data.img?.let { img ->
                img.formats?.let {
                    val imgUrl = when {
                        it.small?.url != null -> it.small?.url
                        it.medium?.url != null -> it.medium?.url
                        it.thumbnail?.url != null -> it.thumbnail?.url
                        it.large?.url != null -> it.large?.url
                        else -> img.url
                    }

                    if (imgUrl != null) {
                        AsyncImage(
                            model = imgUrl,
                            contentDescription = "Image",
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                                .fillMaxWidth()
                                .height(250.dp)
                                .clip(RoundedCornerShape(5.dp)),
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

    }
}